///**
//  * This file is part of the ONEMA ServerlessApiMeetup Package.
//  * For the full copyright and license information,
//  * please view the LICENSE file that was distributed
//  * with this source code.
//  *
//  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
//  *
//  * @author Juan Manuel Torres <kinojman@gmail.com>
//  */
//
//package onema
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
//import com.amazonaws.services.dynamodbv2.document.DynamoDB
//import onema.microb.get.Logic
//import onema.serverlessbase.configuration.cors.Extensions._
//import onema.serverlessbase.configuration.cors.DynamodbCorsConfiguration
//import org.scalatest.{FlatSpec, Matchers}
//
//class GetTest extends FlatSpec with Matchers {
//
//
//  "An Exception" should "generate a valid response" in {
//
//  // Arrange
//    val messageId = "430a160f-67d0-44e1-b25f-103da9548694"
////    val messageId = "4"
//    val corsDomain = "https://foo.com"
//    val dynamodbClient = AmazonDynamoDBAsyncClientBuilder.defaultClient()
//    val tableName = "MicrobItems"
//    val logic = new Logic(tableName, dynamodbClient)
//
//    // Act
//    val response = logic.handleRequest(messageId).withCors(new DynamodbCorsConfiguration(corsDomain, "CorsOrigins", dynamodbClient))
//
//    // Assert
//    response.getHeaders.get("Access-Control-Allow-Origin") should be ("https://foo.com")
//  }
//}
