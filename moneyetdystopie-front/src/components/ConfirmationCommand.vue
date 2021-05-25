<template id="app">
    <section>
        <md-dialog-prompt
                :md-active.sync="active"
                md-title="Je confirme mes achats !"
                md-content="Entrez votre numéro de carte bleu."
                md-input-placeholder="0123456789"
                v-model="cardNumber"
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
                cardNumber: ""
            };
        },
        props: ['customer','reloadAll','serveurErrorMessage','serveurSuccessMessage'],
        methods:{
            async onConfirm () {
                let ok = false;
                let message ={
                    customer: { pseudo: this.customer.pseudo},
                    itemCommands: []
                };
                for(let item of this.customer.cart.items) {
                    message.itemCommands = [
                        ...message.itemCommands,
                        {
                            amount : item.amount,
                            item: { id: item.id}
                        }
                    ]
                }
                await axios.post("/command/create?cardNumber="+this.cardNumber.replace(/ /g,""), message).then(() => {
                    ok = true;
                }).catch(error => {
                    if(error!=null && error.response!=null && error.response.status != 404 && error.response.data!=null){
                        this.serveurErrorMessage('Impossible de confirmer l\'achat : ' + error.response.data);
                    }else{
                        this.serveurErrorMessage('Impossible de confirmer l\'achat.');
                    }
                });
                if(ok){
                    this.customer.cart.items = [];
                    this.reloadAll();
                    this.serveurSuccessMessage("Commande validée.");
                }
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
