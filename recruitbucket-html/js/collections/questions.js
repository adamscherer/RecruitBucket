define([
		'jQuery', 'Underscore', 'Backbone', 'models/question'
], function($, _, Backbone, Model) {
	var Collection = Backbone.Collection.extend({
		model : Model,
		initialize : function() {

		},
		url : function() {
			return '/api/question/all';
		},
		parse : function(resp, xhr) {
			return resp.content;
		}
	});

	return new Collection;
});