define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/recruit',
    'text!templates/recruit.html',
    'views/modals/add-document',
    'views/recruit/reviews',
    'views/recruit/documents',
    'views/recruit/bucket-log',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, RecruitModel, Template) {

    var RecruitCompositeModel = Backbone.Model.extend({

        urlRoot : '/api/recruit/composite'

    });

    var AddDocumentModal = require('views/modals/add-document');

    var ReviewsView = require('views/recruit/reviews');
    var DocumentsView = require('views/recruit/documents');
    var BucketLogView = require('views/recruit/bucket-log');

    var SUBVIEWS = [

    ];

    var View = Backbone.View.extend({

        template : _.template(Template),

        initialize : function() {
            _.bindAll(this, "load", "render", "showEdit", "hideEdit", "save");
        },

        load : function(id) {
            this.model = new RecruitCompositeModel({
                id : id
            });
            this.model.bind("change", this.render);
            this.model.fetch();
        },

        render : function() {
            console.log('render recruit');
            this.$el.html(this.template({
                data : this.model.toJSON()
            }));

            _.each(SUBVIEWS, _.bind(function(view) {
                view.render(this.model);
            }, this));
        },

        events : {
            "click .x-edit" : "showEdit",
            "click #add-document" : "addDocument",
            "click .x-cancel" : "hideEdit",
            "submit form" : "save"
        },

        showEdit : function(ev) {
            this._toggleEditing($(ev.target), 'addClass')

            return false;
        },

        hideEdit : function(ev) {
            this._toggleEditing($(ev.target), 'removeClass')

            return false;
        },

        save : function(ev) {
            console.log('save');
            this.model.set($(ev.currentTarget).serializeObject());
            this.model.save();
            this._toggleEditing($(ev.target), 'removeClass')

            return false;
        },

        addDocument : function(ev) {
            AddDocumentModal.showModal(this.model);

            return false;
        },

        _toggleEditing : function(target, action) {
            var container = target.closest('.recruit-info');
            container[action]('editing');
        }
    });

    return View;
});