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

package onema.microb.delete

import com.amazonaws.serverless.proxy.model.{AwsProxyRequest, AwsProxyResponse}
import com.amazonaws.services.lambda.runtime.Context
import onema.microb.MicrobFunction
import onema.serverlessbase.configuration.cors.DynamodbCorsConfiguration
import onema.serverlessbase.configuration.cors.Extensions.AwsProxyResponseExtension

class Function extends MicrobFunction {

  //--- Fields ---
  private val logic = new Logic(tableName, dynamodbClient)

  //--- Methods ---
  def lambdaHandler(event: AwsProxyRequest, context: Context): AwsProxyResponse = {
    val origin = getOrigin(event)
    val id = event.getPathParameters.get("id")
    handle {
       logic.handleRequest(id)
    }.withCors(DynamodbCorsConfiguration(origin, corsTableName))
  }

}
