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

package onema.microb.create

import com.amazonaws.serverless.proxy.model.{AwsProxyRequest, AwsProxyResponse}
import com.amazonaws.services.lambda.runtime.Context
import onema.core.json.Implicits._
import onema.microb.MicrobFunction
import onema.microb.model.Microb.Item
import onema.serverlessbase.configuration.cors.DynamodbCorsConfiguration
import onema.serverlessbase.configuration.cors.Extensions.AwsProxyResponseExtension
import org.apache.http.HttpStatus

class Function extends MicrobFunction {

  //--- Fields ---
  private val logic = new Logic(tableName, dynamodbClient)

  //--- Methods ---
  def lambdaHandler(event: AwsProxyRequest, context: Context): AwsProxyResponse = {
    val origin = getOrigin(event)
    val item = event.getBody.jsonParse[Item]
    val corsConfiguration = DynamodbCorsConfiguration(origin, corsTableName)

    // Allow valid origins or request that didn't originate from a web browser
    if(corsConfiguration.isOriginValid || origin.isEmpty) {
      handle {
         logic.handleRequest(item, event.getRequestContext.getPath)
      }.withCors(corsConfiguration)
    } else {
      buildError(HttpStatus.SC_FORBIDDEN, s"The orgiin '$origin' is not authorized")
    }
  }
}
