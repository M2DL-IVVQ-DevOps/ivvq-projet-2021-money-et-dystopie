<template>
    <nav class="scene_nav">
        <ol class="scene_nav_list">
            <li class="scene_nav_item">
                <div v-for="(onglet,index) in maxScene"
                     :key="index">
                    <div @click="scene = index" class="scene_nav_button"
                            :class="{'o-active': scene === index}">
                        <div v-if="scene===index" class="texte" :class="{'revert': index%2 != 0}">
                            {{onglet.name}}
                        </div>
                        <img class="icon" v-else :src="onglet.icon" :class="{'revert': index%2 != 0}"/>
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
                scene: 'Accueil',
                maxScene: [
                    {
                        name:'Accueil',
                        icon:'https://www.flaticon.com/svg/vstatic/svg/4440/4440481.svg?token=exp=1617542091~hmac=541b36c8151965f238f11b650fb5558b'
                    },{
                        name:'Mon panier',
                        icon:'https://www.flaticon.com/svg/vstatic/svg/478/478542.svg?token=exp=1617542040~hmac=9433acc44e5591967147e6b1b67d5e8e'
                }
                ]
            }
        },

        mounted () {
            setTimeout(() => {
                this.scene = this.maxScene - 3
            }, 1000)
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
        padding: 65px 0 0 0;
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
        width: 55px;
    }

</style>
