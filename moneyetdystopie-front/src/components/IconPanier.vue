<template>
    <div class="panier">
        <img atl="image panier" class="iconPanier" src="https://www.flaticon.com/svg/vstatic/svg/526/526736.svg?token=exp=1618071588~hmac=ec3151498b2cda612d1e2e2327ce9e7f"/>
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
