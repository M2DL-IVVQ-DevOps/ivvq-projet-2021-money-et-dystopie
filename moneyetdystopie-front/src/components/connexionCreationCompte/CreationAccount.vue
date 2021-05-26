<template id="app">
    <div class="base">
        <form id="accountCreationForm" @submit="checkForm" action="https://vuejs.org/" method="post" novalidate="true">

            <div v-if="errors.length">
                <strong class="error">Veuillez corriger les erreurs suivantes :</strong>
                <ul>
                    <li v-for="(error, index) in errors" v-bind:key="error+index">{{ error }}</li>
                </ul>
            </div>

            <md-field>
                <label>Nom</label>
                <md-input id="lastNameCreation" v-model="lastName" type="text" name="lastName"/>
            </md-field>

            <md-field>
                <label>Prénom</label>
                <md-input id="firstNameCreation" v-model="firstName" type="text" name="firstName"/>
            </md-field>

            <md-field>
                <label>Mail</label>
                <md-input id="emailCreation" v-model="email" type="email" name="email"/>
            </md-field>

            <md-field>
                <label>Mot de passe</label>
                <md-input id="passwordCreation" v-model="password" type="password"></md-input>
            </md-field>

            <md-checkbox class="sellerCheckBox" v-model="seller">Je souhaite être commerçant.</md-checkbox>

            <md-field v-if="seller">
                <label>Nom de ma boutique</label>
                <md-input id="shopCreation" v-model="storeName" type="text"></md-input>
            </md-field>

            <md-checkbox class="customerCheckBox" v-model="customer">Je souhaite être acheteur.</md-checkbox>

            <md-field v-if="customer">
                <label>Pseudo</label>
                <md-input id="nickNameCreation" v-model="pseudo" type="text"></md-input>
            </md-field>
            <md-field v-if="customer">
                <label>Adresse postale</label>
                <md-input id="addressCreation" v-model="address" type="text"></md-input>
            </md-field>
            <div v-if="accountCreationError">
                <label v-text="accountCreationErrorMessage"></label>
            </div>
            <div v-if="customer || seller">
                <md-button id="creationAccountSubmit" v-on:click="creationAccount()">Créer un compte</md-button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name: 'CreationCompte',
        data: function () {
            return {
                errors: [],
                lastName: null,
                firstName: null,
                password: null,
                email: null,
                storeName: null,
                pseudo: null,
                address: null,
                seller: false,
                customer: false,
                accountCreationError: false,
                accountCreationErrorMessage: null
            };
        },
        props: ['creation'],
        methods: {
            async creationAccount(){
                this.checkForm();
                if (this.errors.length){
                    return;
                }
                let userCreation = {
                    lastName: this.lastName,
                    firstName: this.firstName,
                    email: this.email,
                    password: this.password
                };
                if (this.customer){
                    userCreation.customerAccount = {
                        pseudo: this.pseudo,
                        address: this.address
                    }
                }
                if (this.seller){
                    userCreation.sellerAccount = {
                        storeName: this.storeName
                    }
                }
                if(await this.creation(userCreation)){
                    this.purgeFieldsAccountCreation();
                }
            },
            checkForm: function (e) {
                this.errors = [];

                if (!this.lastName) {
                    this.errors.push("Nom requis.");
                }
                if (!this.firstName) {
                    this.errors.push("Prenom requis.");
                }
                if (!this.password) {
                    this.errors.push("Mot de passe requis.");
                }
                if (!this.email) {
                    this.errors.push('Mail requis.');
                } else if (!this.validEmail(this.email)) {
                    this.errors.push('Mail valide requis.');
                }
                if (this.seller && !this.storeName) {
                    this.errors.push("Nom de votre boutique requise.");
                }
                if (this.customer && !this.pseudo) {
                    this.errors.push("Pseudo requis.");
                }
                if (this.customer && !this.address) {
                    this.errors.push("Adresse requise.");
                }

                if (!this.errors.length) {
                    return true;
                }

                e.preventDefault();
            },
            validEmail: function (email) {
                var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            },
            purgeFieldsAccountCreation: function () {
                this.lastName = null;
                this.firstName = null;
                this.password = null;
                this.email = null;
                this.storeName = null;
                this.pseudo = null;
                this.address = null;
                this.seller = false;
                this.customer = false;
                this.error = false;
            }
        }
    }
</script>

<style scoped>
    h2{
        color: black;
    }
    .base{
        background-color: white;
        width: 60%;
        margin: 0 auto;
        border-radius: 30px;
        padding: 50px;
    }
</style>
