<template>
    <div id="events">
        <h1>Event Registration</h1>
        <h2>Schedule New In-Person Event</h2>
        <div>
            <input type="text" placeholder="Name" v-model="newEventName" />
            <input type="date" placeholder="Date" v-model="newEventDate" />
            <input type="time" placeholder="Start Time" v-model="newEventStartTime" />
            <input type="time" placeholder="End Time" v-model="newEventEndTime" />
            <input type="text" placeholder="Registration Limit" v-model="newEventRegLimit" />
            <input type="text" placeholder="Location" v-model="newEventLocation" />
            <button @click="createEvent()" v-bind:disabled="isCreateBtnDisabled">Create Event</button>
            <button class="danger-btn" @click="clearInputs()">Clear</button>
        </div>
        <h2>Events</h2>
        <table>
            <tbody id="events-tbody">
                <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Limit</th>
                </tr>
                <tr v-for="e in events">
                    <td>{{ e.name }}</td>
                    <td>{{ e.date }}</td>
                    <td>{{ e.registrationLimit }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const client = axios.create({
    // IMPORTANT: baseURL, not baseUrl
    baseURL: config.dev.backendBaseUrl
});

export default {
    name: "Events",
    data() {
        return {
            events: [],
            newEventName: null,
            newEventDate: null,
            newEventStartTime: null,
            newEventEndTime: null,
            newEventRegLimit: null,
            newEventLocation: null
        };
    },
    async created() {
        try {
            const response = await client.get("/events");
            this.events = response.data.events
        }
        catch (e) {
            // TODO: show the user a warning
            console.log(e);
        }
    },
    methods: {
        async createEvent() {
            const newEvent = {
                type: "IN_PERSON",
                name: this.newEventName,
                date: this.newEventDate,
                startTime: this.newEventStartTime,
                endTime: this.newEventEndTime,
                registrationLimit: this.newEventRegLimit,
                location: this.newEventLocation
            };
            try {
                const response = await client.post("/events", newEvent);
                this.events.push(response.data);
                this.clearInputs();
            }
            catch (e) {
                // TODO: show the user a warning
                console.log(e);
            }
        },
        clearInputs() {
            this.newEventName = null;
            this.newEventDate = null;
            this.newEventStartTime = null;
            this.newEventEndTime = null;
            this.newEventRegLimit = null;
            this.newEventLocation = null;
        }
    },
    computed: {
        isCreateBtnDisabled() {
            return (
                !this.newEventName
                || !this.newEventDate
                || !this.newEventStartTime
                || !this.newEventEndTime
                || !this.newEventLocation
            );
        }
    }
};
</script>

<style>
#events {
    display: flex;
    flex-direction: column;
    align-items: stretch;
}

h2 {
    padding-top: 1em;
    text-decoration: underline;
}

td,
th {
    padding: 0.5em;
    border: 1px solid black;
}

.danger-btn {
    border: 1px solid red;
    color: red;
}
</style>