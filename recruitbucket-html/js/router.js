define([
    'jQuery',
    'Underscore',
    'Backbone',
    'ViewManager',
    'models/session',
    'views/main',
    'views/recruits',
    'views/geolocation',
    'views/interviewers',
    'views/training',
    'views/recruit',
    'views/review'
], function($, _, Backbone, ViewManager, Session, MainView, RecruitsView, GeolocationView,
    InterviewersView, TrainingView, RecruitView, ReviewView) {
    var AppRouter = Backbone.Router.extend({
        routes : {
            // Define some URL routes
            'main' : 'showMain',
            'recruits' : 'showRecruits',
            'geolocation' : 'showGeolocation',
            'interviewers' : 'showInterviewers',
            'training' : 'showTraining',
            'recruit/:id' : 'showRecruit',
            'recruit/:id/review' : 'showReview',
            'recruit/:id/review/:reviewId' : 'showReview',

            // Default
            '*actions' : 'defaultAction'
        },
        // As above, call render on our loaded module
        // 'views/users/list'
        showMain : function() {
            this.defaultAction();
        },
        showRecruits : function() {
            RecruitsView.render();
        },
        showGeolocation : function() {
            GeolocationView.render();
        },
        showInterviewers : function() {
            InterviewersView.render();
        },
        showTraining : function() {
            TrainingView.render();
        },
        showRecruit : function(id) {
            RecruitView.load(id);
        },
        showReview : function(id, reviewId) {
            ReviewView.load(id);
        },
        defaultAction : function(actions) {
            // We have no matching route, lets display the login page
            ViewManager.renderMain();
        }
    });

    var initialize = function() {
        try {
            var app_router = new AppRouter;
            Backbone.history.start();
        } catch (e) {}
    };
    return {
        initialize : initialize
    };
});
