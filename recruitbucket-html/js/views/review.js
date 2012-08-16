define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/review',
    'models/recruit',
    'text!templates/review.html',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, ReviewModel, RecruitModel, Template) {

    var View = Backbone.View.extend({
        selector : "#body",
        
        template : _.template(Template),
        
        initialize : function() {
            _.bindAll(this, "load", "render", "save");
        },
        
        render : function() {
            console.log('render review');
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    review : this.review.toJSON(),
                    recruit : this.recruit.toJSON()
                }
            })));

            GlobalEvents.trigger('render:sidebar', 'recruits');
        },

        events : {
            "submit form" : "save"
        },

        save : function(ev) {
            this.review.set({recruitId : this.recruit.id});
            this.review.set($(ev.currentTarget).serializeObject());
            this.review.save(null, {
                success : _.bind(function() {
                    Backbone.history.navigate('recruit/' + this.recruit.id, {
                        trigger : true
                    });
                }, this)
            });

            return false;
        },

        load : function(id, reviewId) {
            this.recruit = new RecruitModel({
                id : id
            });
            this.review = new ReviewModel({
                id : reviewId
            });
            
            this.recruit.fetch({
                success : _.bind(function() {
                    this.review.fetch({
                        success : _.bind(function() {
                            this.render()
                        }, this)
                    })
                }, this)
            });
        }
    });

    return new View();
});