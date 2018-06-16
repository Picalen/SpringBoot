import React from 'react';
import PropTypes from 'prop-types';
import { Route } from 'dva/router';
import DocumentTitle from 'react-document-title';
import { Icon } from 'antd';
import GlobalFooter from '../components/GlobalFooter';
import styles from './UserLayout.less';
import { getRouteData } from '../utils/utils';

import logo from '../assets/logo.png';

const links = [{
  title: '帮助',
  // href: '',
}, {
  title: '隐私',
  // href: '',
}, {
  title: '条款',
  // href: '',
}];

const copyright = <div>Copyright <Icon type="copyright" /> Stay with Me</div>;

class UserLayout extends React.PureComponent {
  static childContextTypes = {
    location: PropTypes.object,
  }
  getChildContext() {
    const { location } = this.props;
    return { location };
  }
  componentDidMount() {
    window.particlesJS('particles-js',

      /* 鼠标滑动推开效果，高密度粒子 */
      /* {
          "particles": {
            "number": {
              "value": 80,
              "density": {
                "enable": true,
                "value_area": 800
              }
            },
            "color": {
              "value": "#ffffff"
            },
            "shape": {
              "type": "circle",
              "stroke": {
                "width": 0,
                "color": "#000000"
              },
              "polygon": {
                "nb_sides": 5
              },
              "image": {
                "src": "img/github.svg",
                "width": 100,
                "height": 100
              }
            },
            "opacity": {
              "value": 0.5,
              "random": false,
              "anim": {
                "enable": false,
                "speed": 1,
                "opacity_min": 0.1,
                "sync": false
              }
            },
            "size": {
              "value": 5,
              "random": true,
              "anim": {
                "enable": false,
                "speed": 40,
                "size_min": 0.1,
                "sync": false
              }
            },
            "line_linked": {
              "enable": true,
              "distance": 150,
              "color": "#ffffff",
              "opacity": 0.4,
              "width": 1
            },
            "move": {
              "enable": true,
              "speed": 6,
              "direction": "none",
              "random": false,
              "straight": false,
              "out_mode": "out",
              "attract": {
                "enable": false,
                "rotateX": 600,
                "rotateY": 1200
              }
            }
          },
          "interactivity": {
            "detect_on": "canvas",
            "events": {
              "onhover": {
                "enable": true,
                "mode": "repulse"
              },
              "onclick": {
                "enable": true,
                "mode": "push"
              },
              "resize": true
            },
            "modes": {
              "grab": {
                "distance": 400,
                "line_linked": {
                  "opacity": 1
                }
              },
              "bubble": {
                "distance": 400,
                "size": 40,
                "duration": 2,
                "opacity": 8,
                "speed": 3
              },
              "repulse": {
                "distance": 200
              },
              "push": {
                "particles_nb": 4
              },
              "remove": {
                "particles_nb": 2
              }
            }
          },
          "retina_detect": true,
          "config_demo": {
            "hide_card": false,
            "background_color": "#b61924",
            "background_image": "",
            "background_position": "50% 50%",
            "background_repeat": "no-repeat",
            "background_size": "cover"
          }
        } */

      /* 无关鼠标，粒子密度较少 */
      {
        particles: {
          number: {
            value: 30,
            density: {
              enable: true,
              value_area: 800,
            },
          },
          color: {
            value: '#c8c8c8',
          },
          shape: {
            type: 'circle',
            stroke: {
              width: 0,
              color: '#000000',
            },
            polygon: {
              nb_sides: 5,
            },
            image: {
              src: 'img/github.svg',
              width: 100,
              height: 100,
            },
          },
          opacity: {
            value: 0.5,
            random: false,
            anim: {
              enable: false,
              speed: 1,
              opacity_min: 0.1,
              sync: false,
            },
          },
          size: {
            value: 10,
            random: true,
            anim: {
              enable: false,
              speed: 50,
              size_min: 0.1,
              sync: false,
            },
          },
          line_linked: {
            enable: true,
            distance: 300,
            color: '#c8c8c8',
            opacity: 0.4,
            width: 2,
          },
          move: {
            enable: true,
            speed: 8,
            direction: 'none',
            random: false,
            straight: false,
            out_mode: 'out',
            bounce: false,
            attract: {
              enable: false,
              rotateX: 600,
              rotateY: 1200,
            },
          },
        },
        interactivity: {
          detect_on: 'canvas',
          events: {
            onhover: {
              enable: false,
              mode: 'repulse',
            },
            onclick: {
              enable: false,
              mode: 'push',
            },
            resize: true,
          },
          modes: {
            grab: {
              distance: 800,
              line_linked: {
                opacity: 1,
              },
            },
            bubble: {
              distance: 800,
              size: 80,
              duration: 2,
              opacity: 0.8,
              speed: 3,
            },
            repulse: {
              distance: 400,
              duration: 0.4,
            },
            push: {
              particles_nb: 4,
            },
            remove: {
              particles_nb: 2,
            },
          },
        },
        retina_detect: true,
      }

      /* 鼠标滑动暗黑效果，星空背景 */
      /* {
        "particles": {
        "number": {
          "value": 160,
            "density": {
            "enable": true,
              "value_area": 800
          }
        },
        "color": {
          "value": "#ffffff"
        },
        "shape": {
          "type": "circle",
            "stroke": {
            "width": 0,
              "color": "#000000"
          },
          "polygon": {
            "nb_sides": 5
          },
          "image": {
            "src": "img/github.svg",
              "width": 100,
              "height": 100
          }
        },
        "opacity": {
          "value": 1,
            "random": true,
            "anim": {
            "enable": true,
              "speed": 1,
              "opacity_min": 0,
              "sync": false
          }
        },
        "size": {
          "value": 3,
            "random": true,
            "anim": {
            "enable": false,
              "speed": 4,
              "size_min": 0.3,
              "sync": false
          }
        },
        "line_linked": {
          "enable": false,
            "distance": 150,
            "color": "#ffffff",
            "opacity": 0.4,
            "width": 1
        },
        "move": {
          "enable": true,
            "speed": 1,
            "direction": "none",
            "random": true,
            "straight": false,
            "out_mode": "out",
            "bounce": false,
            "attract": {
            "enable": false,
              "rotateX": 600,
              "rotateY": 600
          }
        }
      },
        "interactivity": {
        "detect_on": "canvas",
          "events": {
          "onhover": {
            "enable": true,
              "mode": "bubble"
          },
          "onclick": {
            "enable": true,
              "mode": "repulse"
          },
          "resize": true
        },
        "modes": {
          "grab": {
            "distance": 400,
              "line_linked": {
              "opacity": 1
            }
          },
          "bubble": {
            "distance": 250,
              "size": 0,
              "duration": 2,
              "opacity": 0,
              "speed": 3
          },
          "repulse": {
            "distance": 400,
              "duration": 0.4
          },
          "push": {
            "particles_nb": 4
          },
          "remove": {
            "particles_nb": 2
          }
        }
      },
        "retina_detect": true
      } */
    );
  }
  getPageTitle() {
    const { location } = this.props;
    const { pathname } = location;
    let title = 'Butterfly';
    getRouteData('UserLayout').forEach((item) => {
      if (item.path === pathname) {
        title = `${item.name} - Butterfly`;
      }
    });
    return title;
  }
  render() {
    return (
      <DocumentTitle title={this.getPageTitle()}>
        <div className={styles.container}>
          <div id="particles-js" className={styles.particles}>
            <canvas className="particles-js-canvas-el" style={{ width: '100%', height: '100%' }} />
          </div>
          <div className={styles.top}>
            <div className={styles.header}>
              <img src={logo} alt="logo" className={styles.logo} />
              <span className={styles.title}>Butterfly</span>
            </div>
            <div className={styles.desc}>十一的个人网站</div>
          </div>
          {
            getRouteData('UserLayout').map(item =>
              (
                <Route
                  exact={item.exact}
                  key={item.path}
                  path={item.path}
                  component={item.component}
                />
              )
            )
          }
          <GlobalFooter className={styles.footer} links={links} copyright={copyright} />
        </div>
      </DocumentTitle>
    );
  }
}

export default UserLayout;
