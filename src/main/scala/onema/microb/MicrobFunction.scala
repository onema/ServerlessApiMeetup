/**
  * This file is part of the ONEMA ServerlessApiMeetup Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <kinojman@gmail.com>
  */

package onema.microb

import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDBAsync, AmazonDynamoDBAsyncClientBuilder}
import com.amazonaws.services.sns.{AmazonSNSAsync, AmazonSNSAsyncClientBuilder}
import onema.core.json.Implicits._
import onema.serverlessbase.configuration.lambda.EnvLambdaConfiguration
import onema.serverlessbase.function.ApiGatewayHandler

trait MicrobFunction extends ApiGatewayHandler with EnvLambdaConfiguration {

  //--- Fields ---
  override protected val snsClient: AmazonSNSAsync = AmazonSNSAsyncClientBuilder.defaultClient()
  protected val dynamodbClient: AmazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder.defaultClient()
  protected val corsTableName: String = getValue("/cors/dynamotable/name").get
  protected val tableName: String = getValue("/dynamotable/name").get

  //--- Methods ---
  protected def getOrigin(event: AwsProxyRequest): Option[String] = {
    val origin = Option(event.getHeaders.get("origin"))
    log.info(event.javaClassToJson)
    log.info(tableName)
    log.info(corsTableName)
    log.info(origin.getOrElse("Origin not set"))
    origin
  }
}
