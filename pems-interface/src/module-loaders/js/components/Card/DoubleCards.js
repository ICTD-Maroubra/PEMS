import React from "react";

import SingleCard from "./SingleCard";

export default class DoubleCards extends React.Component {

  render() {
return (
<div>
    <div class="split2">
      <SingleCard title={this.props.title1} content={this.props.content1} unit={this.props.unit1}/>
    </div>
    <div class="split2">
      <SingleCard title={this.props.title2} content={this.props.content2} unit={this.props.unit2}/>
    </div>
</div>
);
  }
}
