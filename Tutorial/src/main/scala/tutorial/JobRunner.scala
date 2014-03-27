/*
 * Copyright (c) 2012 SnowPlow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package tutorial

// Hadoop

// Scalding
import com.twitter.scalding.Tool
import org.apache.hadoop.util.ToolRunner
import org.apache.hadoop.conf.Configuration

/**
 * Entrypoint for Hadoop to kick off the job.
 *
 * Borrowed from com.twitter.scalding.Tool
 */
object JobRunner {
  def main(args : Array[String]) {
    ToolRunner.run(new Configuration, new Tool, args.head +: "--local" +: args.tail)
  }
}