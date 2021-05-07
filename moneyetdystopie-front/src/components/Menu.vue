<template>
    <nav class="scene_nav">
        <ol class="scene_nav_list">
            <li class="scene_nav_item">
                <div v-for="(onglet,index) in maxScene"
                     :key="index">
                    <div @click="selectionMenu(index, onglet.navigation)" class="scene_nav_button"
                            :class="{'o-active': scene === index}">
                        <div v-if="scene===index" class="text" :class="{'revert': index%2 != 0}">
                            {{onglet.name}}
                        </div>
                        <img class="icon" v-else :src="getImage(onglet.icon)" :class="{'revert': index%2 != 0}" alt="Icone d'article"/>
                    </div>
                </div>
            </li>
        </ol>
    </nav>
</template>

<script>
    export default {
        name : 'Menu',
        data () {
            return {
                scene: 'CATALOG',
                maxScene: [
                    {
                        name:'Catalog',
                        icon:'dollar',
                        navigation: 'CATALOG'
                    },{
                        name:'Mon panier',
                        icon:'panier',
                        navigation: 'CART'
                    },{
                        name:'Ma boutique',
                        icon:'panier',
                        navigation: 'SHOP'
                    }
                ]
            }
        },
        props: ['changeNavigation'],
        mounted () {
            setTimeout(() => {
                this.scene = this.maxScene - 3
            }, 1000)
        },
        methods: {
            getImage(img){
                var images = require.context('../assets/', false, /\.svg$/)
                return images('./' + img + ".svg")
            },
            selectionMenu(index, nav){
                this.scene = index;
                this.changeNavigation(nav);
            }
        }
    }
</script>

<style scoped>
    .scene_nav {
        width: 80%;
        margin: 0 10%;
    }

    .scene_nav_list {
        display: flex;
        margin: 0;
        padding: 0;
        transform: scaleY(0.6);
    }

    .scene_nav_item {
        display: flex;
    }
    .scene_nav_item :nth-child(even) {
        transform: scaleY(-1);
    }
    .scene_nav_item :not(:first-child) {
        margin-left: -8px;
    }

    .scene_nav_button {
        display: block;
        width: 140px;
        height: 140px;
        padding: 52px 0 0 0;
        border: none;
        background: #9a96b2;
        outline: none;
        cursor: pointer;
        transition: 500ms;
        clip-path: polygon(70px 0,calc(100% - 70px) 0,100% 100%,0 100%);
    }

    .revert {
        transform: scaleY(-1);
    }

    .o-active {
        width: 250px;
        background: #ffd246;
    }

    .o-active :nth-child(even) {
        transform: scaleY(-1);
    }

    .scene_nav_button:hover {
        background: #ffd246;
    }

    .icon{
        width: 45px;
    }

    .text{
        padding-bottom: 7px;
        margin-top: 7px;
    }

</style>
