import React from "react";

import SingleIconCard from "./SingleIconCard";

export default class DoubleIconCards extends React.Component {

  render() {
return (
<div>
    <div class="split2">
      <SingleIconCard icon={this.props.icon2}title={this.props.title1}/>
    </div>
    <div class="split2">
      <SingleIconCard icon={this.props.icon2} title={this.props.title2}/>
    </div>
</div>
);
  }
}
