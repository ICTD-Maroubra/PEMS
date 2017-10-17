import React from "react";

export default class SingleCard extends React.Component {

  render() {
    console.log(this.props);
    return (

<div class="card">
  <div class="card-title">
    {this.props.title}
  </div>
  <div class="card-content">
    {this.props.content}
    {this.props.unit}
  </div>
</div>


    );
  }
}
