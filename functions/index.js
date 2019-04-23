const functions = require('firebase-functions');

const request = require('request-promise')

exports.indexBooksToElastic = functions.database.ref('/books/{bookID}')
	.onWrite((change,context) => {
		let bookData = change.after.val();
		let bookID = context.params.bookID;
		
		console.log('Indexing book:', bookData);
		
		let elasticSearchConfig = functions.config().elasticsearch;
		let elasticSearchUrl = elasticSearchConfig.url + 'books/book/' + bookID;
		let elasticSearchMethod = bookData ? 'POST' : 'DELETE';
		
		let elasticSearchRequest = {
			method: elasticSearchMethod,
			url: elasticSearchUrl,
			auth:{
				username: elasticSearchConfig.username,
				password: elasticSearchConfig.password,
			},
			body: bookData,
			json: true
		  };
		  
		  return request(elasticSearchRequest).then(response => {
			console.log("ElasticSearch response", response);
			return response;
		  });
	});


