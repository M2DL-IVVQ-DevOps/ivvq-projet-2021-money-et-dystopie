<template id="app">
    <div v-if="!itemInCreation" class="div-button-action-new-item">
        <md-button v-on:click="beginCreation()" class="md-primary md-raised" >Ajouter un article à ma boutique</md-button>
    </div>
    <div v-else class="base">
        <div class="div-button-action-new-item">
            <md-button class="button-action-close" v-on:click="endCreation()">Annuler</md-button>
        </div>
        <form class="form">
            <div v-if="errors.length">
                <strong class="errors">Veuillez corriger les erreurs suivantes :</strong>
                <ul>
                    <li v-for="(error, index) in errors" v-bind:key="error+index">{{ error }}</li>
                </ul>
            </div>

            <md-field>
                <label>Titre</label>
                <md-input v-model="title" type="text" name="title" class="title"/>
            </md-field>

            <md-field>
                <label>Description</label>
                <md-input v-model="description" type="text" name="description" class="description"></md-input>
            </md-field>

            <md-field>
                <label>Image (URL)</label>
                <md-input v-model="picture" type="text" name="picture" class="picture"></md-input>
            </md-field>

            <md-field>
                <label>Prix</label>
                <md-input v-model="price" type=number step=0.01 name="price" class="price"></md-input>
            </md-field>

            <md-field>
                <label>Quantité</label>
                <md-input v-model="amount" type=number step=1 name="amount" class="amount"></md-input>
            </md-field>

            <div>
                <md-button class="button-add-item" v-on:click="addItem()">Ajouter</md-button>
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
        props: ['seller', 'changeServeurErrorMessage', 'getAllItemsForCatalogue'],
        methods: {
            checkForm: function () {
                this.errors = [];

                if (!this.title || this.title.length < 2 || this.title.length > 100) {
                    this.errors.push('Le titre doit faire entre 2 et 100 caractère.');
                }
                if (!this.picture) {
                    this.errors.push('Image requise.');
                }
                if (!this.description || this.description.length < 2 || this.description.length > 200) {
                    this.errors.push("La description doit faire entre 10 et 200 caractère.");
                }
                if (!this.amount || this.amount<0) {
                    this.errors.push("La quantité doit être renseignée.");
                }
                if (!this.price || this.price<0) {
                    this.errors.push("Le prix doit être renseignée.");
                }
            },
            addItem: function () {
                this.checkForm();
                if(this.errors.length){
                    return;
                }
                const message ={
                    title: this.title,
                    picture: this.picture,
                    amount: this.amount,
                    price: this.price,
                    description: this.description,
                    sellerAccount: {"storeName": this.seller.storeName}
                };
                axios.post(
                    "/item/create", message).then(response => {
                    this.seller.items = [...this.seller.items,
                            {
                                id: response.data.id,
                                amount: response.data.amount,
                                description: response.data.description,
                                picture: response.data.picture,
                                price: response.data.price,
                                title: response.data.title,
                                sellerAccount:{storeName: response.data.sellerAccount.storeName}
                            }];
                        this.getAllItemsForCatalogue();
                        this.endCreation();
                    })
                    .catch(error => {
                        if(error!=null && error.response!=null && error.response.data!=null){
                            this.changeServeurErrorMessage('Impossible d\'enregistrer l\'article du côté du serveur  : ' + error.response.data);
                        }else{
                            this.changeServeurErrorMessage('Impossible d\'enregistrer l\'article du côté du serveur.');
                        }
                    });
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
    .div-button-action-new-item{
        text-align: right;
        margin: 0 10% 0 0;
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
