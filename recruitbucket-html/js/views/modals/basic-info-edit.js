define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/modals/recruit-edit-basic.html',
    'jQuery_serialize',
    'jQuery_chosen'
], function($, _, Backbone, GlobalEvents, template) {

    var visible;

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'showModal', 'changed', 'saveChanges');
            
            $('#modals').append(this.$el);
        },

        render : function() {
            this.$el.html(this.template(this.model.toJSON()));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : false
            }).on('hide', this.saveChanges);
            
            $(".chzn-select").chosen(); 
            $(".chzn-select-deselect").chosen({allow_single_deselect:true});
        },

        showModal : function(recruit) {
            this.model = recruit;
            this.render();
            this.errorMessage.addClass('hide');
            this.modalEl.modal('show');
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        },

        events : {
            "change input" : "changed",
            "change select" : "changed"
        },

        changed : function(ev) {
            console.log('changed');
            //this.model.set($(ev.currentTarget).serializeObject());
        },

        saveChanges : function(ev) {
            console.log('save');
            this.model.set($(ev.currentTarget).serializeObject());
            this.model.save();
        }
    });

    // Return an instantiate view to ensure there is only one per page.
    return new View();
});