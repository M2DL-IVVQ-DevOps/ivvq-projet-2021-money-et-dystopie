<template>
  <div id="app" class="size-app" :class="{
          'catalog-background': user && navigation === 'CATALOG',
          'card-background': user && navigation === 'CART',
          'shop-background': user && navigation === 'SHOP',
          'connexionCreationAccount-background': !user}">
    <h1>Achète de l'argent avec ton argent !</h1>

    <section v-if="user">
      <div v-if="user.customer != null && navigation === 'CATALOG'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif" alt="Image de roulette d'argent"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Items
                :changeCart="addInCart"
                :itemsData="itemsData"
                :navigation="navigation"
        />
      </div>
      <div v-if="user.customer != null &&  navigation === 'CART'">
        <img src="https://cdn.dribbble.com/users/4228/screenshots/12480182/media/f53ab0258be8992e124d9b9a62c9107d.jpg?compress=1&resize=1000x750" alt="Image de livraison d'argent"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <Items
                :changeCart="changeInCart"
                :itemsData="user.customer.cart.items"
                :navigation="navigation"
        />
      </div>
      <div v-if="user.seller != null && navigation === 'SHOP'">
        <img src="https://cdn.dribbble.com/users/673247/screenshots/9066054/media/b20471249151a406ecc4ef44481ad8ae.png?compress=1&resize=1000x750" alt="Image de sélection d'argent"/>
        <Menu :changeNavigation="changeNavigation"></Menu>
        <AddItem :seller="user.seller"></AddItem>
        <Items
                :itemsData="user.seller.items"
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
  import AddItem from './components/AddItem.vue';
  import ConnexionCreationAccount from "./components/connexionCreationCompte/ConnexionCreationAccount";

  export default {
    name: 'App',
    components: {
      ConnexionCreationAccount,
      Items,
      Menu,
      AddItem
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
            items: []
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

      creationAccount(){
        this.user = {
          email: 'monMail',
          password: 'azert',
          lastName: 'FRANZESE',
          firstName: 'Alessandra',
          seller: {
            storeName: 'Le crochet de Nina',
            items: []
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
        if (amountRestante === 0){
          index = this.user.customer.cart.items.indexOf(elementInPanier);
          if(index!==-1){
            elementSelect.amount += elementInPanier.amount;
            this.user.customer.cart.items.splice(index, 1);
          }
        }else{
          amountRetire = elementInPanier.amount - amountRestante;
          elementInPanier.amount = amountRestante;
          elementSelect.amount += amountRetire;
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
  .catalog-background{
    background-color: #24212b;
  }
  .shop-background{
    background-color: #004bb5;
  }
  .connexionCreationAccount-background{
    background-color: #242b45;
  }
  .card-background{
    background-color: #f8cedc;
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
