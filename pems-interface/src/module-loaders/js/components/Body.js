import React from "react";

import PageTitle from "./Widgets/PageTitle";

export default class Body extends React.Component {

  render() {
    return (
      <div class="body">
      <PageTitle title={this.props.title}/>
      </div>
    );
  }
}
