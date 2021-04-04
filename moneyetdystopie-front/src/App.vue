<template>
  <div id="app">
    <IconPanier></IconPanier>
    <h1>Achète de l'argent avec ton argent !</h1>
    <img alt="Vue logo" src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif">
    <Menu></Menu>
    <Articles
            :ajouterDansPanier="ajouterDansPanier"
            :articlesData="articlesData"
    ></Articles>
  </div>
</template>

<script>
  import Articles from './components/Articles.vue';
  import Menu from './components/Menu.vue';
  import IconPanier from "./components/IconPanier";

  export default {
    name: 'App',
    components: {
      IconPanier,
      Articles,
      Menu
    },
    methods: {
      ajouterDansPanier(idElement, quantiteSelection) {
        let elementSelectionneDejaPanier = this.user.panier.find(elt => elt.id == idElement);
        let elementSelectionne = this.articlesData.find(elt => elt.id == idElement);

        switch (quantiteSelection) {
          case 0 :
            if(elementSelectionneDejaPanier != null){
              elementSelectionne.quantite += elementSelectionneDejaPanier.quantite;
              this.user.panier.splice(this.user.panier.indexOf(elt=>elt.id == idElement), 1);
            }
            break;
          default:
            if(elementSelectionneDejaPanier != null){
              elementSelectionneDejaPanier.quantite += quantiteSelection;
            }else {
              this.user.panier = [...this.user.panier, {id: idElement, quantite: quantiteSelection}];
            }
            elementSelectionne.quantite -= quantiteSelection;
            break;
        }
      }
    },
    data: function () {
      return {
        user: {
          panier : []
        },
        articlesData : [{
          id : 1,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/10850904/scratch-r.gif"
        },{
          id: 2,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/6672180/sloth.gif"
        },{
          id: 3,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/14239844/dribbble.jpg?compress=1&resize=800x600"
        },{
          id: 4,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/10864129/win-r.gif"
        },{
          id: 5,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/5700236/artboard_24.png?compress=1&resize=800x600"
        },{
          id: 6,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/6672180/sloth.gif"
        },{
          id: 7,
          price : 10,
          quantite : 10,
          title : "Mon titre",
          img : "https://cdn.dribbble.com/users/427368/screenshots/14239844/dribbble.jpg?compress=1&resize=800x600"
        }
        ]
      }
    },
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap');
  #app{
    text-align: center;
    padding: 50px 0;
  }
  body{
    background-color: #24212b;
  }
  h1{
    color: white;
    position: absolute;
    font-family: 'Permanent Marker', cursive;
    font-size: 7em;
    width: 40%;
    line-height: 1em;
  }
</style>
