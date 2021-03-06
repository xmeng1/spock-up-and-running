/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package squawker.notify

import spock.lang.Specification
import spock.lang.Subject
import squawker.User
import squawker.notify.email.EmailSender

class NewFollowerNotifierSpec3 extends Specification {

  @Subject notifier = new NewFollowerNotifier()

  // tag::argument-capture[]
  def "sends email to user when someone follows them"() {
    given:
    def emailSender = Mock(EmailSender)
    notifier.emailSender = emailSender

    and:
    def message // <1>

    when:
    notifier.onNewFollower(event)

    then:
    1 * emailSender.send(user1, _) >> { // <2>
      message = it[1] // <3>
    }

    and: // <4>
    message.from == "admin@squawker.io"
    message.subject == "You have a new follower!"
    message.template == "new-follower"
    message.follower == user2.username

    where:
    user1 = new User("spock")
    user2 = new User("kirk")
    event = new NewFollowerEvent(user1, user2)
  }
  // end::argument-capture[]

}
