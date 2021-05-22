<template>
    <div class="commands">
        <div v-for="command in commands" :key="command.id">
            <CommandItems :command="command"></CommandItems>
        </div>
        <div v-if="noitem()" class="no" >
            Aucun article
        </div>
    </div>
</template>

<script>

    import CommandItems from "./CommandItems";
    export default {
        name: "Commands",
        components: {
            CommandItems
        },
        props:['commands'],
        methods: {
            noitem(){
                return this.commands.length == 0;
            }
        }
    }
</script>

<style scoped>
    .commands{
        margin: 0 10%;
        width: 80%;
    }
    .no{
        color: white;
        margin: 0 auto;
    }
</style>
