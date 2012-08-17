define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/modals/add-recruit-modal.html',
    'jQuery_chosen',
    'jQuery_ui',
    'jQuery_tagedit',
    'autoGrowInput'
], function($, _, Backbone, GlobalEvents, template) {

    // Since this data is submitted to an IFRAME, we are adding an accessible
    // callback function.
    var callbackFunctionName = 'addRecruitCallback';
    var callback = function(data) {
        instance.hideModal();
        Backbone.history.navigate('recruit/' + data.id, {
            trigger : true
        });
    };

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'render');

            $('#modals').append(this.$el);
            window[callbackFunctionName] = callback;
        },

        render : function() {
            this.$el.html(this.template({}));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : true
            });

            $('.show-tags2').tagedit({
                allowEdit: false,
                allowAdd: false,
                autocompleteOptions: {
                    source: function(request, response){
                        var data = [
                            { "id": "1", "label": "Hazel Grouse", "value": "Hazel Grouse" },
                            { "id": "2", "label": "Common Quail",   "value": "Common Quail" },
                            { "id": "3", "label": "Greylag Goose", "value": "Greylag Goose" },
                            { "id": "4", "label": "Merlin", "value": "Merlin" }
                        ];
                        return response($.ui.autocomplete.filter(data, request.term) );

                    }

                }
            });

            $( "#school" ).typeahead({
                source: function(typeahead, query) {
                    return $.get('/api/search/college', { q: query }, function(data) {
                        console.log(data);
                        return data.values;
                    });
                },
                matcher: function() {
                    return true;
                },

                select: function( event, ui ) {
                    log( ui.item ?
                        "Selected: " + ui.item.value + " aka " + ui.item.id :
                        "Nothing selected, input was " + this.value );
                }
            });
            //$(".chzn-select").chosen();
            //$(".chzn-select-deselect").chosen({
            //    allow_single_deselect : true
            //});
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        }
    });

    // Return an instantiate view to ensure there is only one per page.
    var instance = new View();

    return instance;
});