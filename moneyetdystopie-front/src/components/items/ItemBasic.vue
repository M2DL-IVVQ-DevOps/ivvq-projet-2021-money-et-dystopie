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
    </md-card>
</template>

<script>
    export default {
        name: 'ItemBasic',
        data: function () {
            return {
                amountSelect: 0
            }
        },
        mounted() {
            this.amountSelect = this.itemData.amount;
        },
        props: ['itemData'],
        methods: {
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
    .picture{
        object-fit: cover;
        height: 150px;
    }
</style>
