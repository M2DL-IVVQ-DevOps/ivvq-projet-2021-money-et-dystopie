<template>
  <div id="app" :class="{'catalogue': utilisateur, 'connexionCreationCompte': !utilisateur}">
    <h1>Achète de l'argent avec ton argent !</h1>
    <section v-if="utilisateur">
      <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif"/>
      <IconPanier></IconPanier>
      <Menu></Menu>
      <Articles
              :ajouterDansPanier="ajouterDansPanier"
              :articlesData="articlesData"
      />
    </section>
    <section v-else>
      <ConnexionCreationCompte :connexion="connexionCompte" :creation="creationCompte"></ConnexionCreationCompte>
    </section>
  </div>
</template>

<script>
  import Articles from './components/Articles.vue';
  import Menu from './components/Menu.vue';
  import IconPanier from "./components/IconPanier";
  import ConnexionCreationCompte from "./components/connexionCreationCompte/ConnexionCreationCompte";

  export default {
    name: 'App',
    components: {
      ConnexionCreationCompte,
      IconPanier,
      Articles,
      Menu
    },
    mounted: function () {
      this.$nextTick(function () {
        this.utilisateur = null;
      })
    },
    methods: {
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
        let elementSelectionneDejaPanier = this.utilisateur.acheteur.commande.articles.find(elt => elt.id == idElement);
        let elementSelectionne = this.articlesData.find(elt => elt.id == idElement);

        switch (quantiteSelection) {
          case 0 :
            if(elementSelectionneDejaPanier != null){
              elementSelectionne.quantite += elementSelectionneDejaPanier.quantite;
              this.utilisateur.acheteur.commande.articles.splice(this.utilisateur.acheteur.commande.articles.indexOf(elt=>elt.id == idElement), 1);
            }
            break;
          default:
            if(elementSelectionneDejaPanier != null){
              elementSelectionneDejaPanier.quantite += quantiteSelection;
            }else {
              this.utilisateur.acheteur.commande.articles = [...this.utilisateur.acheteur.commande.articles, {id: idElement, quantite: quantiteSelection}];
            }
            elementSelectionne.quantite -= quantiteSelection;
            break;
        }
      }
    },
    data: function () {
      return {
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
</style>
