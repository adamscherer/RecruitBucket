define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GoogleMaps',
    'GlobalEvents',
    'text!templates/geolocation.html'
], function($, _, Backbone, GoogleMaps, GlobalEvents, Template) {

    var View = Backbone.View.extend({
        selector : "#body",

        template : _.template(Template),

        render : function() {

            console.log('render geolocation');
            $(this.selector).html(this.$el.html(this.template({

            })));

            var mapCanvas = $("#utopia-google-map-5").get(0);

            GoogleMaps.addMapToCanvas(mapCanvas);

            GlobalEvents.trigger('render:sidebar', 'geolocation');
        }
    });

    return new View();
});
