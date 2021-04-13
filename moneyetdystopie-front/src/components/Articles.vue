<template>
    <div class="articles">
        <div v-for="article in articlesData" :key="article.id">
            <div v-if="navigation ==='CATALOGUE'">
                <ArticleCatalogue :selectionArticle="changeDansPanier" :articleData="article"></ArticleCatalogue>
            </div>
            <div v-else>
                <ArticlePanier :selectionArticle="changeDansPanier" :articleData="article"></ArticlePanier>
            </div>
        </div>
        <div v-if="aucunArticle()" class="aucun" >
            Aucun article
        </div>
    </div>
</template>

<script>
    import ArticleCatalogue from './ArticleCatalogue.vue';
    import ArticlePanier from './ArticlePanier.vue';

    export default {
        name: "Articles",
        components: {
            ArticleCatalogue,
            ArticlePanier
        },
        props:['changeDansPanier', 'articlesData', 'navigation'],
        methods: {
            aucunArticle(){
                return this.articlesData.length == 0;
            }
        }
    }
</script>

<style scoped>
    .articles{
        margin: 0 10%;
        width: 80%;
        display: flex;
        flex-wrap: wrap;
    }
    .aucun{
        color: white;
        margin: 0 auto;
    }
</style>
