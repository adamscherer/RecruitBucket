define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/recruit',
    'text!templates/recruit.html',
    'views/modals/basic-info-edit',
    'views/modals/add-document',
    'views/recruit/reviews',
    'views/recruit/documents',
    'views/recruit/bucket-log',
], function($, _, Backbone, GlobalEvents, RecruitModel, Template) {

    var BasicEditModal = require('views/modals/basic-info-edit');
    var AddDocumentModal = require('views/modals/add-document');
    
    var ReviewsView = require('views/recruit/reviews');
    var DocumentsView = require('views/recruit/documents');
    var BucketLogView = require('views/recruit/bucket-log');

    var SUBVIEWS = [
        new ReviewsView(),
        new DocumentsView(),
        new BucketLogView()
    ];

    var View = Backbone.View.extend({
        selector : "#body",
        
        template : _.template(Template),

        initialize : function() {
            _.bindAll(this, "load", "render", "basicEdit");
        },

        render : function() {
            console.log('render recruit');
            $(this.selector).html(this.$el.html(this.template({
                data : this.model.toJSON()
            })));

            _.each(SUBVIEWS, _.bind(function(view) {
                view.render(this.model);
            }, this));

            this.delegateEvents();
            GlobalEvents.trigger('render:sidebar', 'recruits');
        },

        events : {
            "click #edit-basic" : "basicEdit",
            "click #add-document" : "addDocument"
        },

        basicEdit : function(ev) {
            BasicEditModal.showModal(this.model);

            return false;
        },

        addDocument : function(ev) {
            AddDocumentModal.showModal(this.model);

            return false;
        },
        
        load : function(id) {
            this.model = new RecruitModel({
                id : id
            });
            this.model.bind("change", this.render);
            this.model.fetch();
        }
    });

    return new View();
});