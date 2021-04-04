<template>
    <div class="articles">
        <div v-for="article in articlesData" :key="article.id">
            <Article :ajouterDansPanier="ajouterDansPanier" :articleData="article"></Article>
        </div>
    </div>
</template>

<script>
    import Article from './Article.vue';

    export default {
        name: "Articles",
        components: {
            Article
        },
        props:['ajouterDansPanier', 'articlesData' ]
    }
</script>

<style scoped>
    .articles{
        margin: 0 10%;
        width: 80%;
        display: flex;
        flex-wrap: wrap;
    }
</style>
