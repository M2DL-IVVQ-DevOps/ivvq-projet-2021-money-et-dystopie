<template>
    <nav class="scene_nav">
        <ol class="scene_nav_list">
            <li class="scene_nav_item">
                <div v-for="(onglet,index) in maxScene"
                     :key="index">
                    <div @click="selectionMenu(index, onglet.navigation)" class="scene_nav_button o-active">
                        <div class="text" :class="{'revert': index%2 != 0}">
                            {{onglet.name}}
                        </div>
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
                        navigation: 'CATALOG',
                        valid: 'all'
                    },{
                        name:'Mon panier',
                        navigation: 'CART',
                        valid: 'customer'
                    },{
                        name:'Ma boutique',
                        navigation: 'SHOP',
                        valid: 'seller'
                    },{
                        name:'Mes achats',
                        navigation: 'MY_COMMANDS',
                        valid: 'customer'
                    }
                ]
            }
        },
        props: ['changeNavigation', 'isSeller', 'isCustomer'],
        mounted () {
            this.maxScene = this.maxScene.filter(scene => this.isValid(scene.valid));
        },
        methods: {
            selectionMenu(index, nav){
                this.scene = index;
                this.changeNavigation(nav);
            },
            isValid(validity){
                return ((validity === 'all') || (validity === 'customer' && this.isCustomer) || (validity === 'seller' && this.isSeller));
            },
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
        font-size: 18px;
        width: 140px;
        height: 100px;
        padding: 30px 0 0 0;
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

    .text{
        padding-bottom: 7px;
        margin-top: 7px;
    }

</style>
