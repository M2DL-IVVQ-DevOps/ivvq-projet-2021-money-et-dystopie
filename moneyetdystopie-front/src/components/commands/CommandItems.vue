<template>
    <md-card class="card">
        <md-card-content>
            <md-card-media class="background">
                <p class="title">id : {{command.id}} - <span class="state">{{command.state}}</span></p>
            </md-card-media>
            <Items
                    :changeCart="null"
                    :itemsData="command.itemCommands"
                    :navigation="null"
            ></Items>
        </md-card-content>
    </md-card>
</template>

<script>
    import Items from "../items/Items";

    export default {
        name: 'CommandItems',
        components: {Items},
        props: ['command']
    }
</script>

<style scoped>
    .card{
        min-width: 100%;
        margin: 10px;
    }
    .background{
        background-color: #a3a3f3;
        color: white;
        padding: 10px 0;
    }
    .title{
        color: #634c63;
        font-size: 1.4em;
    }
    .state{
        color: white;
    }
</style>
