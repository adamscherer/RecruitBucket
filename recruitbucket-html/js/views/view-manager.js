define([
    'require',
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'views/main',
    'views/modals/add-recruit'
], function(require, $, _, Backbone, GlobalEvents) {

    var MainView = require('views/main');
    var AddRecruitModal = require('views/modals/add-recruit');
    
    var body = $("#body");
    var modalArea = $("#body");
    var instances = {
        main : new MainView()
    };

    var ViewManager = {
        init : function() {
            
        },
        renderMain : function() {
            instances.main.render(body);
            GlobalEvents.trigger('render:sidebar', 'dashboard');
        },
        showAddRecruitModal : function() {
            AddRecruitModal.render();
        }
    };

    _.extend(ViewManager, Backbone.Events);

    return ViewManager;
});