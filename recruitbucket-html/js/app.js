define([
    'jQuery',
    'Underscore',
    'Backbone',
    'router',
    'models/session',
    'Bootstrap',
    'views/navigation',
    'views/modals/login'
], function($, _, Backbone, Router, Session) {

    var Navigation = require('views/navigation');
    var LoginModalView = require('views/modals/login');

    var initialize = function() {
        this.mainBody = $('#main-body');

        this.loginModal = new LoginModalView();

        this.sidebarView = new Navigation.sidebar({
            el : $("#sidebar")
        });

        this.headerView = new Navigation.header({
            el : $("#header")
        });

        Session.bind('change:auth', function(authenticated) {
            if (authenticated) {
                Router.initialize();

                this.headerView.render();

                this.mainBody.removeClass('hide');
            } else {
                this.mainBody.addClass('hide');
            }
        }.bind(this));

        Session.getAuth();
    }

    return {
        initialize : initialize
    };
});
