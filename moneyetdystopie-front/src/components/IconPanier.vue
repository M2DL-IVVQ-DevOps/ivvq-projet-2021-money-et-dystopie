<template>
    <div class="panier">
        <img class="iconPanier" src="https://www.flaticon.com/svg/vstatic/svg/3378/3378015.svg?token=exp=1617540539~hmac=223676048c4798303e07de446f8850e8"/>
        <p class="texte">Panier</p>
    </div>
</template>

<script>
    export default {
        name: "IconPanier"
    }
</script>

<style scoped>
    .iconPanier{
        width: 100px;
    }
    .panier{
        position: fixed;
        right: 50px;
    }
    .texte{
        color: white;
    }
</style>
