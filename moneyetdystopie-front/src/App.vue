<template>
  <div id="app" class="size-app" :class="{
          'catalog-background': user && navigation === 'CATALOG',
          'card-background': user && navigation === 'CART',
          'shop-background': user && navigation === 'SHOP',
          'command-background': user && navigation === 'MY_COMMANDS',
          'connexionCreationAccount-background': !user}">
    <h1>Achète de l'argent avec ton argent !</h1>

    <section v-if="user">
      <div v-if="navigation === 'CATALOG'">
        <img src="https://cdn.dribbble.com/users/427368/screenshots/10846214/slot-r.gif" alt="Image de roulette d'argent"/>
        <Menu :changeNavigation="changeNavigation" :isSeller="user.sellerAccount !== null" :isCustomer="user.customerAccount !== null"></Menu>
        <Items
                :changeCart="addInCart"
                :itemsData="catalogue"
                :navigation="navigation"
                :isOnlySeller="user.sellerAccount !== null && user.customerAccount === null"
        />
      </div>
      <div v-if="user.customerAccount != null &&  navigation === 'CART'">
        <img src="https://cdn.dribbble.com/users/4228/screenshots/12480182/media/f53ab0258be8992e124d9b9a62c9107d.jpg?compress=1&resize=1000x750" alt="Image de livraison d'argent"/>
        <Menu :changeNavigation="changeNavigation" :isSeller="user.sellerAccount !== null" :isCustomer="user.customerAccount !== null"></Menu>
        <ConfirmationCommand
                v-if="user.customerAccount.cart.items.length"
                :customer="user.customerAccount"
                :getAllItemsForCatalogue="getAllItemsForCatalogue"
                :changeServeurErrorMessage="setErrorMessage"
                :getPastCommands="getPastCommands"
        ></ConfirmationCommand>
        <Items
                :changeCart="changeInCart"
                :itemsData="user.customerAccount.cart.items"
                :navigation="navigation"
        />
      </div>
      <div v-if="user.sellerAccount != null && navigation === 'SHOP'">
        <img src="https://cdn.dribbble.com/users/673247/screenshots/9066054/media/b20471249151a406ecc4ef44481ad8ae.png?compress=1&resize=1000x750" alt="Image de sélection d'argent"/>
        <Menu :changeNavigation="changeNavigation" :isSeller="user.sellerAccount !== null" :isCustomer="user.customerAccount !== null"></Menu>
        <AddItem :getAllItemsForCatalogue="getAllItemsForCatalogue" :serveurErrorMessage="setErrorMessage" :serveurSuccessMessage="setSuccessMessage" :seller="user.sellerAccount"></AddItem>
        <Items
                :itemsData="user.sellerAccount.items"
                :navigation="navigation"
        />
      </div>
      <div v-if="user.customerAccount != null && navigation === 'MY_COMMANDS'">
        <img src="https://cdn.dribbble.com/users/175166/screenshots/15251076/media/e7a79bca2405cafe3ea4155a87098073.jpg?compress=1&resize=1000x750" alt="Image de sélection d'argent"/>
        <Menu :changeNavigation="changeNavigation" :isSeller="user.sellerAccount !== null" :isCustomer="user.customerAccount !== null"></Menu>
        <Commands :commands="user.customerAccount.pastCommands"></Commands>
      </div>
    </section>

    <section v-else>
      <ConnexionCreationAccount :connexion="connexionAccount" :creation="creationAccount"></ConnexionCreationAccount>
    </section>

    <md-snackbar md-position="center" :md-duration="8000" :md-active.sync="showServeurErrorMessage" md-persistent style="background-color: #BA240F">
      <span>{{serveurErrorMessage}}</span>
      <md-button class="button-action" v-on:click="closeSnackBar()">X</md-button>
    </md-snackbar>

    <md-snackbar md-position="center" :md-duration="8000" :md-active.sync="showSuccessMessage" md-persistent  style="background-color: #13650E">
      <span>{{successMessage}}</span>
      <md-button class="button-action" v-on:click="closeSnackBar()">X</md-button>
    </md-snackbar>
  </div>
</template>

<script>
  import Items from './components/items/Items.vue';
  import Menu from './components/Menu.vue';
  import AddItem from './components/AddItem.vue';
  import ConnexionCreationAccount from "./components/connexionCreationCompte/ConnexionCreationAccount";
  import Commands from "./components/commands/Commands";
  import ConfirmationCommand from "./components/ConfirmationCommand";
  import axios from "axios";

  export default {
    name: 'App',
    components: {
      ConfirmationCommand,
      Commands,
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
      connexionAccount : async function(userConnexion){
        await axios.post('/token/create/', userConnexion).then(response => {
          this.user = response.data;
          this.initUser();
          this.getAllItemsForCatalogue();
          this.getPastCommands();
        }).catch((error) =>{
          if(error !== null && error.response !== null && error.response.status != 404 && error.response.data !== null){
            this.setErrorMessage("Connexion impossible : " + error.response.data);
          }else{
            this.setErrorMessage("Connexion impossible : Erreur serveur");
          }
        });
      },
      creationAccount : async function(userCreation){
        try {
          await axios.post('/user/create/', userCreation);
          this.setSuccessMessage("Votre compte a bien été créé.");
          return true;
        }catch(error)  {
          if(error !== null && error.response !== null && error.response.status != 404 && error.response.data !== null){
            this.setErrorMessage("Impossible de créer le compte : " + error.response.data)
          }else{
            this.setErrorMessage("Impossible de créer le compte : Erreur serveur")
          }
          return false;
        }
      },

      addInCart(idElement, amountSelection) {
        let elementInPanier = this.user.customerAccount.cart.items.find(elt => elt.id === idElement);
        let elementSelect = this.catalogue.find(elt => elt.id === idElement);

        if(elementInPanier != null){
           elementInPanier.amount += amountSelection;
        }else if(amountSelection>0){
          this.user.customerAccount.cart.items = [...this.user.customerAccount.cart.items, {...elementSelect, amount: amountSelection}];
        }
        elementSelect.amount -= amountSelection;
      },

      changeInCart(idElement, amountRestante) {
        let elementInPanier = this.user.customerAccount.cart.items.find(elt => elt.id === idElement);
        let elementSelect = this.catalogue.find(elt => elt.id === idElement);
        let amountRetire, index;
        if (amountRestante === 0){
          index = this.user.customerAccount.cart.items.indexOf(elementInPanier);
          if(index!==-1){
            elementSelect.amount += elementInPanier.amount;
            this.user.customerAccount.cart.items.splice(index, 1);
          }
        }else{
          amountRetire = elementInPanier.amount - amountRestante;
          elementInPanier.amount = amountRestante;
          elementSelect.amount += amountRetire;
        }
      },

      getPastCommands(){
        axios.get('/customer/getPastCommands/').then(response => {
          this.user.customerAccount.pastCommands = response.data;
          for(let pastCommand of this.user.customerAccount.pastCommands) {
            let listItemCommands = [];
            for(let itemCommand of pastCommand.itemCommands) {
              listItemCommands = [...listItemCommands,
                {
                  ...itemCommand.item,
                  'amount': itemCommand.amount
                }];
            }
            pastCommand.itemCommands = listItemCommands;
          }
        }).catch( error => {
          if(error!=null && error.response!=null && error.response.status != 404 && error.response.data!=null){
            this.setErrorMessage('Impossible de récupérer les commandes passées : ' + error.response.data);
          }else{
            this.setErrorMessage('Impossible de récupérer les commandes passées.');
          }
        });
      },

      closeSnackBar(){
        this.showServeurErrorMessage = false;
        this.showSuccessMessage = false;
      },

      setErrorMessage(message){
        this.showServeurErrorMessage = true;
        this.serveurErrorMessage = message;
      },

      setSuccessMessage(message){
        this.showSuccessMessage = true;
        this.successMessage = message;
      },

      async getAllItemsForCatalogue(){
        await axios.get('/item/all').then(response => {
          this.catalogue = [...response.data];
        }).catch(error => {
          if(error !== null && error.response !== null && error.response.status != 404 && error.response.data !== null) {
            this.setErrorMessage("Récupération du catalogue d'articles impossible : " + error.response.data);
          }else{
            this.setErrorMessage("Récupération du catalogue d'articles impossible : Erreur serveur");
          }
        });
        if(this.user.sellerAccount !== null){
          this.user.sellerAccount.items = [];
          for(let item of this.catalogue){
            if(item.sellerAccount.storeName === this.user.sellerAccount.storeName){
              this.user.sellerAccount.items = [...this.user.sellerAccount.items, item];
            }
          }
        }
      },
      initUser(){
        if (this.user !== null && this.user.customerAccount !== null && this.user.customerAccount.cart === null){
          this.user.customerAccount.cart = {
            items: []
          };
        }
      }
    },
    data: function () {
      return {
        navigation: 'CATALOG',
        user: null,
        catalogue: [],
        serveurErrorMessage: "",
        successMessage: "",
        showServeurErrorMessage: false,
        showSuccessMessage: false,
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
  body {
    background-color: #24212b;
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
  .command-background{
    background-color: #c5c6ff;
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
  .button-action {
    background: #9a96b2;
    color: white;
  }
  .button-action:hover {
    background: #ffd246;
    color: black;
  }
</style>
