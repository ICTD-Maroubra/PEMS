import React from "react";

export default class SigleCard extends React.Component {

  render() {
    return (
      <article class="c-card c-card--wide">

     <header class="c-card__header">
       <img src="http://placehold.it/300x250"  class="c-card__image" alt="Card Image" />
     </header>

     <div class="c-card__body">
       <h2 class="c-card__title">
         card-wide
       </h2>
       <p class="c-card__subtitle">
         wide-modifier
       </p>
       <p class="c-card__intro">
         Lorem ipsum dolor sit amet, consectetur adipisicing elit.
       </p>
     </div>

     <footer class="c-card__footer">
       Footer
     </footer>

   </article>

    );
  }
}
