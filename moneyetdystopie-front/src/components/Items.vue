<template>
    <div class="items">
        <div v-for="item in itemsData" :key="item.id">
            <div v-if="navigation ==='CATALOG'">
                <ItemCatalog :selectionItem="changeCart" :itemData="item"></ItemCatalog>
            </div>
            <div v-if="navigation ==='CART'">
                <ItemCart :selectionItem="changeCart" :itemData="item"></ItemCart>
            </div>
            <div v-if="navigation ==='SHOP'">
                <ItemBoutique :selectionItem="changeCart" :itemData="item"></ItemBoutique>
            </div>
        </div>
        <div v-if="noitem() && navigation !=='SHOP'" class="no" >
            Aucun article
        </div>
    </div>
</template>

<script>
    import ItemCatalog from './ItemCatalog.vue';
    import ItemCart from './ItemCart.vue';
    import ItemBoutique from './ItemBoutique.vue';

    export default {
        name: "Items",
        components: {
            ItemCatalog,
            ItemCart,
            ItemBoutique
        },
        props:['changeCart', 'itemsData', 'navigation'],
        methods: {
            noitem(){
                return this.itemsData.length == 0;
            }
        }
    }
</script>

<style scoped>
    .items{
        margin: 0 10%;
        width: 80%;
        display: flex;
        flex-wrap: wrap;
    }
    .no{
        color: white;
        margin: 0 auto;
    }
</style>
