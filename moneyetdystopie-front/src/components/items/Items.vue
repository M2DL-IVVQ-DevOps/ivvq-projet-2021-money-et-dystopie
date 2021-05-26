<template>
    <div class="items">
        <div v-for="item in itemsData" :key="item.id">
            <div v-if="navigation ==='CATALOG' && !isOnlySeller">
                <ItemCatalog :selectionItem="changeCart" :itemData="item"></ItemCatalog>
            </div>
            <span v-else>
                <div v-if="navigation ==='CART'">
                    <ItemCart :selectionItem="changeCart" :itemData="item"></ItemCart>
                </div>
                <div v-else>
                    <ItemBasic :selectionItem="changeCart" :itemData="item"></ItemBasic>
                </div>
            </span>
        </div>
        <div v-if="noitem()" class="no" >
            Aucun article
        </div>
    </div>
</template>

<script>
    import ItemCatalog from './ItemCatalog.vue';
    import ItemCart from './ItemCart.vue';
    import ItemBasic from './ItemBasic.vue';

    export default {
        name: "Items",
        components: {
            ItemCatalog,
            ItemCart,
            ItemBasic
        },
        props:['changeCart', 'itemsData', 'navigation', 'isOnlySeller'],
        methods: {
            noitem(){
                return this.itemsData === null || this.itemsData.length === 0;
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
