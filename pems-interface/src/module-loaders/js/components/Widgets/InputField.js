import React from "react";

export default class InputField extends React.Component {

  handleChange(e){
    const title = e.target.value;
    this.props.changeTitle(title);
  }

  render() {
    return (
      <div>
      <input value={this.props.title} onChange={this.handleChange.bind(this)} />
      </div>
    );
  }
}
