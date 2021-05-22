<template id="app">
    <section>
        <md-dialog-prompt
                :md-active.sync="active"
                md-title="Je confirme mes achats !"
                md-content="Entrez votre numéro de carte bleu."
                md-input-placeholder="0123456789"
                md-confirm-text="J'achète"
                md-cancel-text="Non"
                @md-cancel="onCancel"
                @md-confirm="onConfirm" />
        <div class="div-button-action-confirmation">
            <md-button @click="active = true" class="md-primary md-raised" >Confirmer la commande</md-button>
        </div>
    </section>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'ConfirmationCommand',
        data: function () {
            return {
                active: false,
            };
        },
        props: ['customer','getAllItemsForCatalogue','changeServeurErrorMessage'],
        methods:{
            onConfirm () {
                let message ={
                    customer: { pseudo: 'dsfdsfsd2' /*this.customer.pseudo*/},
                    itemCommands: []
                };
                for(let i=0; i<this.customer.cart.items.length; i++){
                    message.itemCommands = [
                        ...message.itemCommands,
                        {
                            amount : this.customer.cart.items[i].amount,
                            item: { id: this.customer.cart.items[i].id}
                        }
                    ]
                }
                axios.post("/command/create", message).then(() => {
                    this.customer.cart.items = [];
                    this.getAllItemsForCatalogue();
                }).catch(error => {
                    if(error!=null && error.response!=null && error.response.data!=null){
                        this.changeServeurErrorMessage('Impossible de confirmer l\'achat : ' + error.response.data);
                    }else{
                        this.changeServeurErrorMessage('Impossible de confirmer l\'achat.');
                    }
                });
            },
            onCancel () {
                this.value = 'Disagreed'
            }
        }
    }
</script>

<style scoped>
    .div-button-action-confirmation{
        text-align: right;
        margin: 0 10% 0 0;
    }
</style>
