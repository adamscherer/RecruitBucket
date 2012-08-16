define([
		'jQuery', 'Underscore', 'Backbone', 'models/recruit'
], function($, _, Backbone, Model) {
	var Collection = Backbone.Collection.extend({
		model : Model,
		initialize : function() {

		},
		url : function() {
			return '/api/recruit/all';
		},
		parse : function(resp, xhr) {
			return resp.content;
		}
	});

	return new Collection;
});