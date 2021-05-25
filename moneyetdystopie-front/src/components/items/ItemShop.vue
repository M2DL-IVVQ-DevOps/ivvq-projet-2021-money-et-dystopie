<template >
    <md-card class="card">
        <md-card-content>
            <md-card-media>
                <img class="picture" :src="itemData.picture" alt="Avatar" @error="imageLoadError">
                <div class="seller">{{ itemData.sellerAccount.storeName}}</div>
                <div class="title">{{ itemData.title }}</div>
                <div class="price">{{ itemData.price }}</div>
                <p>Quantité : {{itemData.amount}}</p>
            </md-card-media>
        </md-card-content>
        Modifier la disponibilité :
        <md-card-actions>
            <md-field class="cardAction">
                <md-input v-model="amountSelect"></md-input>
            </md-field>
            <md-button class="cardAction md-button" v-on:click="updateItem()">MODIFIER</md-button>
        </md-card-actions>
    </md-card>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'ItemShop',
        data: function () {
            return {
                amountSelect: 0
            }
        },
        mounted() {
            this.amountSelect = this.itemData.amount;
        },
        props: ['itemData', 'serveurErrorMessage', 'serveurSuccessMessage', 'reloadAll'],
        methods: {
            updateItem(){
                axios.post("/item/amount/", {...this.itemData, amount: this.amountSelect}).then(() => {
                    this.serveurSuccessMessage("Article modifié.");
                    this.reloadAll();
                }).catch(error => {
                    if(error!=null && error.response!=null && error.response.status != 404 && error.response.data!=null){
                        this.serveurErrorMessage('Impossible de modifier l\'article : ' + error.response.data);
                    }else{
                        this.serveurErrorMessage('Impossible de modifier l\'article.');
                    }
                });
            },
            imageLoadError(){
                this.itemData.picture = "https://st3.depositphotos.com/23594922/31822/v/600/depositphotos_318221368-stock-illustration-missing-picture-page-for-website.jpg";
            }
        }
    }
</script>

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
        width: 50%;
    }
    .picture{
        object-fit: cover;
        height: 150px;
    }
    .md-field .md-input{
        margin-left: 10px;
    }
</style>
