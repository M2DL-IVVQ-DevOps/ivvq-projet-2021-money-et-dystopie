<template>
  <div id="app" class="size-app" :class="{'catalog': user, 'connexionCreationAccount': !user}">
    <h1>Achète de l'argent avec ton argent !</h1>

    <section v-if="user">
      <div v-if="navigation === 'CATALOG'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Items
                :changeCart="addInCart"
                :itemsData="itemsData"
                :navigation="navigation"
        />
      </div>
      <div v-if="navigation === 'CART'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Items
                :changeCart="changeInCart"
                :itemsData="user.customer.cart.items"
                :navigation="navigation"
        />
      </div>
    </section>

    <section v-else>
      <ConnexionCreationAccount :connexion="connexionAccount" :creation="creationAccount"></ConnexionCreationAccount>
    </section>
  </div>
</template>

<script>
  import Items from './components/Items.vue';
  import Menu from './components/Menu.vue';
  import ConnexionCreationAccount from "./components/connexionCreationCompte/ConnexionCreationAccount";

  export default {
    name: 'App',
    components: {
      ConnexionCreationAccount,
      Items,
      Menu
    },
    mounted: function () {
      this.$nextTick(function () {
        this.user = null;
      })
    },
    methods: {
      changeNavigation(nav){
        this.navigation = nav;
      },

      connexionAccount(){
        this.user = {
          email: 'monMail',
          password: 'azert',
          lastName: 'FRANZESE',
          firstName: 'Alessandra',
          seller: {
            storeName: 'Le crochet de Nina',
          },
          customer:{
            pseudo: 'Rozen',
            adress: '54 rue jenesaisou, TOULOUSE',
            cart: {
              id: '2152',
              state: 'IN_PROGRESS',
              items: []
            },
            pastOrder: null,
          }
        };
        console.log("creation");
      },

      creationAccount(){
        this.user = {
          email: 'monMail',
          password: 'azert',
          lastName: 'FRANZESE',
          firstName: 'Alessandra',
          seller: {
            storeName: 'Le crochet de Nina',
          },
          customer:{
            pseudo: 'Rozen',
            adress: '54 rue jenesaisou, TOULOUSE',
            cart: {
              id: '2152',
              state: 'IN_PROGRESS',
              items: []
            },
            pastOrder: null,
          }
        };
      },

      addInCart(idElement, amountSelection) {
        let elementInPanier = this.user.customer.cart.items.find(elt => elt.id == idElement);
        let elementSelect = this.itemsData.find(elt => elt.id == idElement);

        if(elementInPanier != null){
           elementInPanier.amount += amountSelection;
        }else if(amountSelection>0){
          this.user.customer.cart.items = [...this.user.customer.cart.items, {...elementSelect, amount: amountSelection}];
        }
        elementSelect.amount -= amountSelection;
      },
      changeInCart(idElement, amountRestante) {
        let elementInPanier = this.user.customer.cart.items.find(elt => elt.id === idElement);
        let elementSelect = this.itemsData.find(elt => elt.id === idElement);
        let amountRetire, index;
        switch (amountRestante) {
          case 0 :
            index = this.user.customer.cart.items.indexOf(elementInPanier);
            if(index!=-1){
              elementSelect.amount += elementInPanier.amount;
              this.user.customer.cart.items.splice(index, 1);
            }
            break;
          default:
            amountRetire = elementInPanier.amount - amountRestante;
            elementInPanier.amount = amountRestante;
            elementSelect.amount += amountRetire;
            break;
        }
      }
    },
    data: function () {
      return {
        navigation: 'CATALOG',
        user: null,
        itemsData : [{
          id : 1,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/10850904/scratch-r.gif"
        },{
          id: 2,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/6672180/sloth.gif"
        },{
          id: 3,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/14239844/dribbble.jpg?compress=1&resize=800x600"
        },{
          id: 4,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/10864129/win-r.gif"
        },{
          id: 5,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/5700236/artboard_24.png?compress=1&resize=800x600"
        },{
          id: 6,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/6672180/sloth.gif"
        },{
          id: 7,
          price : 10,
          amount : 10,
          title : "Mon titre",
          picture : "https://cdn.dribbble.com/users/427368/screenshots/14239844/dribbble.jpg?compress=1&resize=800x600"
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
  .catalog{
    background-color: #24212b;
  }
  .connexionCreationAccount{
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
