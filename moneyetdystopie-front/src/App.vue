<template>
  <div id="app" class="size-app" :class="{'catalogue': utilisateur, 'connexionCreationCompte': !utilisateur}">
    <h1>Achète de l'argent avec ton argent !</h1>

    <section v-if="utilisateur">
      <div v-if="navigation === 'CATALOGUE'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Articles
                :changeDansPanier="ajouterDansPanier"
                :articlesData="articlesData"
                :navigation="navigation"
        />
      </div>
      <div v-if="navigation === 'PANIER'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Articles
                :changeDansPanier="retirerDansPanier"
                :articlesData="utilisateur.acheteur.commande.articles"
                :navigation="navigation"
        />
      </div>
    </section>

    <section v-else>
      <ConnexionCreationCompte :connexion="connexionCompte" :creation="creationCompte"></ConnexionCreationCompte>
    </section>
  </div>
</template>

<script>
  import Articles from './components/Articles.vue';
  import Menu from './components/Menu.vue';
  import ConnexionCreationCompte from "./components/connexionCreationCompte/ConnexionCreationCompte";

  export default {
    name: 'App',
    components: {
      ConnexionCreationCompte,
      Articles,
      Menu
    },
    mounted: function () {
      this.$nextTick(function () {
        this.utilisateur = null;
      })
    },
    methods: {
      changeNavigation(nav){
        this.navigation = nav;
      },
      connexionCompte(){
        this.utilisateur = {
          mail: 'monMail',
          mdp: 'azert',
          nom: 'FRANZESE',
          prenom: 'Alessandra',
          commercant: {
            nomBoutique: 'Le crochet de Nina',
          },
          acheteur:{
            pseudo: 'Rozen',
            adresse: '54 rue jenesaisou, TOULOUSE',
            commande: {
              id: '2152',
              etat: 'EN_COURS',
              articles: []
            }
          }
        };
      },
      creationCompte(){
        this.utilisateur = {
          mail: 'monMail',
          mdp: 'azert',
          nom: 'FRANZESE',
          prenom: 'Alessandra',
          commercant: {
            nomBoutique: 'Le crochet de Nina',
          },
          acheteur:{
            pseudo: 'Rozen',
            adresse: '54 rue jenesaisou, TOULOUSE',
            commande: {
              id: '2152',
              etat: 'EN_COURS',
              articles: []
            }
          }
        };
      },
      ajouterDansPanier(idElement, quantiteSelection) {
        let elementDansPanier = this.utilisateur.acheteur.commande.articles.find(elt => elt.id == idElement);
        let elementSelectionne = this.articlesData.find(elt => elt.id == idElement);

        switch (quantiteSelection) {
          case 0 :
            if(elementDansPanier != null){
              elementSelectionne.quantite += elementDansPanier.quantite;
              this.utilisateur.acheteur.commande.articles.splice(this.utilisateur.acheteur.commande.articles.indexOf(elt=>elt.id == idElement), 1);
            }
            break;
          default:
            if(elementDansPanier != null){
              elementDansPanier.quantite += quantiteSelection;
            }else {
              this.utilisateur.acheteur.commande.articles = [...this.utilisateur.acheteur.commande.articles, {...elementSelectionne, quantite: quantiteSelection}];
            }
            elementSelectionne.quantite -= quantiteSelection;
            break;
        }
      },
      retirerDansPanier(idElement, quantiteRestante) {
        let elementDansPanier = this.utilisateur.acheteur.commande.articles.find(elt => elt.id == idElement);
        let elementSelectionne = this.articlesData.find(elt => elt.id == idElement);
        let quantiteRetire;

        switch (quantiteRestante) {
          case 0 :
            elementSelectionne.quantite += elementDansPanier.quantite;
            this.utilisateur.acheteur.commande.articles.splice(this.utilisateur.acheteur.commande.articles.indexOf(elt=>elt.id == idElement), 1);
            break;
          default:
            quantiteRetire = elementDansPanier.quantite - quantiteRestante;
            elementDansPanier.quantite = quantiteRestante;
            elementSelectionne.quantite += quantiteRetire;
            break;
        }
      }
    },
    data: function () {
      return {
        navigation: 'CATALOGUE',
        utilisateur: null,
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
  .catalogue{
    background-color: #24212b;
  }
  .connexionCreationCompte{
    background-color: #242b45;
  }
  h1{
    color: white;
    position: absolute;
    font-family: 'Permanent Marker', cursive;
    font-size: 7em;
    width: 40%;
    line-height: 1em;
  }
  h2{
    color: white;
    font-size: 2em;
  }
  .cursor{
    cursor: pointer;
  }
  .size-app{
    min-height: 100%;
  }
</style>
