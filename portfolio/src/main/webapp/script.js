// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random fun fact to the page
  */
function displayFunFact(){
  const facts =
      ['I was born in Weehawken, New Jersey, and lived on a houseboat on the Hudson river!', 'I love the outdoors. My hobbies include trail running, hiking, skiing, camping, and climbing 14ers!', 'I like to wake up with the sun, and am a morning person. I love the quietness of 6am.', 'I really enjoy reading. Urban Fantasy, like the Dresden Files, are some of my favorite reads!'];

  // Pick a fun fact.
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}
