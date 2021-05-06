<template id="app">
    <div v-if="!itemInCreation">
        <md-button class="button-add-item" v-on:click="beginCreation()">Ajouter un article à ma boutique</md-button>
    </div>
    <div v-else class="base">
        <md-button class="button-add-item" v-on:click="endCreation()">Annuler</md-button>
        <form @submit="checkForm" action="https://vuejs.org/" method="post" novalidate="true">
            <div v-if="errors.length">
                <b>Please correct the following error(s):</b>
                <ul>
                    <li v-for="(error, index) in errors" v-bind:key="error+index">{{ error }}</li>
                </ul>
            </div>

            <md-field>
                <label>Titre</label>
                <md-input v-model="title" type="text" name="title"/>
            </md-field>

            <md-field>
                <label>Description</label>
                <md-input v-model="description" type="text" name="description"></md-input>
            </md-field>

            <md-field>
                <label>Image (URL)</label>
                <md-input v-model="picture" type="text" name="picture"></md-input>
            </md-field>

            <md-field>
                <label>Prix</label>
                <md-input v-model="price" type="text" name="price"></md-input>
            </md-field>

            <md-field>
                <label>Quantité</label>
                <md-input v-model="amount" type="text" name="amount"></md-input>
            </md-field>

            <div>
                <md-button v-on:click="addItem()">Ajouter</md-button>
            </div>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'AddItem',
        data: function () {
            return {
                itemInCreation: false,
                errors: [],
                description: null,
                title: null,
                picture: null,
                price: null,
                amount: null,
            };
        },
        props: ['storeName'],
        methods: {
            checkForm: function (e) {
                this.errors = [];

                if (!this.password) {
                    this.errors.push("Mot de passe requis.");
                }
                if (!this.email) {
                    this.errors.push('email requis.');
                }

                if (!this.errors.length) {
                    return true;
                }

                e.preventDefault();
            },
            addItem: function () {
                const message ={
                    title: this.title,
                    picture: this.picture,
                    amount: this.amount,
                    price: this.price,
                    description: this.description,
                    sellerAccount: {"storeName": "Lecrochet1" /*this.storeName*/}
                };
                axios.post(
                    "http://localhost:8080/item/create", message, {
                        headers: {
                            'Access-Control-Allow-Origin' : '*',
                            'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
                            'Content-Type': 'application/json'
                        }
                    }).then(response => {
                        console.log("OK",response);
                    })
                    .catch(error => {
                        console.log("KO",error);
                    });
                /* .finally(() => this.loading = false)*/
                this.endCreation();
            },
            beginCreation(){
                this.itemInCreation = true;
            },
            endCreation(){
                this.itemInCreation = false;
            }
        }
    }
</script>

<style scoped>
    .button-add-item {
        background: #9a96b2;
        color: white;
    }
    .button-add-item:hover {
        background: #ffd246;
        color: black;
    }
    h2{
        color: black;
    }
    .base{
        background-color: white;
        width: 60%;
        margin: 0 auto;
        border-radius: 30px;
        padding: 50px;
    }
</style>
