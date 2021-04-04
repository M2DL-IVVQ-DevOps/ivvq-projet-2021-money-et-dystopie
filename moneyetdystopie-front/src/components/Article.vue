<template >
    <md-card class="card">
        <md-card-content>

            <md-card-media>
                <img :src="articleData.img" alt="Avatar">
                <div class="title">{{ articleData.title }}</div>
                <div class="price">{{ articleData.price }}</div>
            </md-card-media>

        </md-card-content>

        <md-card-actions>
            <md-field clas="cardAction">
                <select v-model="quantiteSelection" >
                    <option :value="index-1" v-for="index in articleData.quantite+1" :key="index" >{{index-1}}</option>
                </select>
            </md-field>
            <md-button clas="cardAction" v-on:click="selectionnerPourPanier()" class="md-button">AJOUTER</md-button>
        </md-card-actions>
    </md-card>
</template>

<script>
    export default {
        name: 'Article',
        data: function () {
            return {
                quantiteSelection: 0
            }
        },
        props: ['articleData', 'ajouterDansPanier'],
        methods: {
            selectionnerPourPanier(){
                this.ajouterDansPanier(this.articleData.id, this.quantiteSelection);
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .md-button{
        background-color: #ffd246;
        border-radius: 8px;
    }
    .card{
        width: 200px;
        margin: 10px;
    }
    .title{
        color: #634c63;
        font-size: 1.4em;
    }
    .price{
        color: #634c63;
        font-size: 1.8em;
    }
    .cardAction{
        width: 45%;
        margin: auto
    }
</style>
