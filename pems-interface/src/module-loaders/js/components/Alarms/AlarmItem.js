import React from "react";

export default class AlarmItem extends React.Component {

  constructor()
  {
    super();

  }

  render() {
    const warningIcon ="../../assets/images/edited/warning.png";
    const errorIcon ="../../assets/images/edited/error.png";
    const AlarmItemType = this.props.type;

    let selectedIcon;
    if (AlarmItemType === "Warning"){
      selectedIcon = ( <img class="alarm-item-icon" src={warningIcon} />)
    } else if (AlarmItemType === "Error"){
      selectedIcon = ( <img class="alarm-item-icon" src={errorIcon} /> )
    } else {
      selectedIcon = ( <img class="alarm-item-icon" src={warningIcon} />)
    }


    return (
      <div class="alarm-item">
<table class="alarmitemtable">
<tr>
<td class="alarmitem-icon"> {selectedIcon} </td>
<td class="alarmitem-content">{this.props.content}</td>
</tr>
</table>
      </div>
    );
  }

}
