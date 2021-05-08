<template >
    <md-card class="card">
        <md-card-content>

            <md-card-media>
                <img class="picture" :src="itemData.picture" alt="Avatar" @error="imageLoadError">
                <div class="seller">{{ itemData.sellerAccount.storeName}}</div>
                <div class="title">{{ itemData.title }}</div>
                <div class="price">{{ itemData.price }}</div>
            </md-card-media>

        </md-card-content>

        <md-card-actions>
            <md-field class="cardAction">
                <select v-model="amountSelect" >
                    <option :value="index-1" v-for="index in itemData.amount+1" :key="index" >{{index-1}}</option>
                </select> <span class="max-amount"> / {{itemData.amount}}</span>
            </md-field>
            <md-button v-on:click="selectForCart()" class="md-button">AJOUTER</md-button>
        </md-card-actions>
    </md-card>
</template>

<script>
    export default {
        name: 'Item',
        data: function () {
            return {
                amountSelect: 0
            }
        },
        props: ['itemData', 'selectionItem'],
        methods: {
            selectForCart(){
                let amount = this.amountSelect;
                this.amountSelect = 0;
                this.selectionItem(this.itemData.id, amount);
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
    .max-amount{
        margin-top: 6px;
        font-size: 18px;
        width: 50%;
    }
    .picture{
        object-fit: cover;
        height: 150px;
    }
</style>
