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
                <span v-else>
                     <div v-if="navigation ==='SHOP'">
                        <ItemShop :itemData="item"
                                  :serveurErrorMessage="serveurErrorMessage"
                                  :serveurSuccessMessage="serveurSuccessMessage"
                                  :reloadAll="reloadAll"></ItemShop>
                     </div>
                     <div v-else>
                        <ItemBasic :selectionItem="changeCart" :itemData="item"></ItemBasic>
                     </div>
                </span>
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
    import ItemShop from "./ItemShop.vue";

    export default {
        name: "Items",
        components: {
            ItemShop,
            ItemCatalog,
            ItemCart,
            ItemBasic
        },
        props:['changeCart', 'itemsData', 'navigation', 'isOnlySeller', 'serveurErrorMessage', 'serveurSuccessMessage', 'reloadAll'],
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
